package com.ericsson.cifwk.avs



import org.junit.*
import grails.test.mixin.*

@TestFor(RequirementController)
@Mock(Requirement)
class RequirementControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/requirement/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.requirementInstanceList.size() == 0
        assert model.requirementInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.requirementInstance != null
    }

    void testSave() {
        controller.save()

        assert model.requirementInstance != null
        assert view == '/requirement/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/requirement/show/1'
        assert controller.flash.message != null
        assert Requirement.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/requirement/list'


        populateValidParams(params)
        def requirement = new Requirement(params)

        assert requirement.save() != null

        params.id = requirement.id

        def model = controller.show()

        assert model.requirementInstance == requirement
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/requirement/list'


        populateValidParams(params)
        def requirement = new Requirement(params)

        assert requirement.save() != null

        params.id = requirement.id

        def model = controller.edit()

        assert model.requirementInstance == requirement
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/requirement/list'

        response.reset()


        populateValidParams(params)
        def requirement = new Requirement(params)

        assert requirement.save() != null

        // test invalid parameters in update
        params.id = requirement.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/requirement/edit"
        assert model.requirementInstance != null

        requirement.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/requirement/show/$requirement.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        requirement.clearErrors()

        populateValidParams(params)
        params.id = requirement.id
        params.version = -1
        controller.update()

        assert view == "/requirement/edit"
        assert model.requirementInstance != null
        assert model.requirementInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/requirement/list'

        response.reset()

        populateValidParams(params)
        def requirement = new Requirement(params)

        assert requirement.save() != null
        assert Requirement.count() == 1

        params.id = requirement.id

        controller.delete()

        assert Requirement.count() == 0
        assert Requirement.get(requirement.id) == null
        assert response.redirectedUrl == '/requirement/list'
    }
}
