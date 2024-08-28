package com.ericsson.cifwk.avs



import org.junit.*
import grails.test.mixin.*

@TestFor(AvsController)
@Mock(Avs)
class AvsControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/avs/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.avsInstanceList.size() == 0
        assert model.avsInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.avsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.avsInstance != null
        assert view == '/avs/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/avs/show/1'
        assert controller.flash.message != null
        assert Avs.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/avs/list'


        populateValidParams(params)
        def avs = new Avs(params)

        assert avs.save() != null

        params.id = avs.id

        def model = controller.show()

        assert model.avsInstance == avs
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/avs/list'


        populateValidParams(params)
        def avs = new Avs(params)

        assert avs.save() != null

        params.id = avs.id

        def model = controller.edit()

        assert model.avsInstance == avs
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/avs/list'

        response.reset()


        populateValidParams(params)
        def avs = new Avs(params)

        assert avs.save() != null

        // test invalid parameters in update
        params.id = avs.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/avs/edit"
        assert model.avsInstance != null

        avs.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/avs/show/$avs.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        avs.clearErrors()

        populateValidParams(params)
        params.id = avs.id
        params.version = -1
        controller.update()

        assert view == "/avs/edit"
        assert model.avsInstance != null
        assert model.avsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/avs/list'

        response.reset()

        populateValidParams(params)
        def avs = new Avs(params)

        assert avs.save() != null
        assert Avs.count() == 1

        params.id = avs.id

        controller.delete()

        assert Avs.count() == 0
        assert Avs.get(avs.id) == null
        assert response.redirectedUrl == '/avs/list'
    }
}
